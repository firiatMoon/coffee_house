package com.capybara.coffee_house.services;

import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.entities.ClientBonusCardTransaction;
import com.capybara.coffee_house.entities.Order;
import com.capybara.coffee_house.enums.BonusPointsAction;
import com.capybara.coffee_house.enums.TransactionType;
import com.capybara.coffee_house.repositories.ClientBonusCardTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ClientBonusCardTransactionService {
    private final BonusCardService bonusCardService;
    private final ClientBonusCardTransactionRepository clientBonusCardTransactionRepository;
    private final ClientBonusNotificationService clientBonusNotificationService;

    @Autowired
    public ClientBonusCardTransactionService(BonusCardService bonusCardService, ClientBonusCardTransactionRepository clientBonusCardTransactionRepository, ClientBonusNotificationService clientBonusNotificationService) {
        this.bonusCardService = bonusCardService;
        this.clientBonusCardTransactionRepository = clientBonusCardTransactionRepository;
        this.clientBonusNotificationService = clientBonusNotificationService;
    }

    @Transactional
    public void createTransactionFromOrder(Order order) {
        BonusCard bonusCard = bonusCardService.getByClientId(order.getClient().getId());
        ClientBonusCardTransaction.ClientBonusCardTransactionBuilder bonusCardTransactionBuilder =
                ClientBonusCardTransaction.builder();

        TransactionType transactionType;
        BigDecimal points;
        switch (order.getBonusPointsAction()){
            case USE:
                transactionType = TransactionType.DEBIT;
                points = bonusCard.getAmount();
                bonusCard.setAmount(BigDecimal.ZERO);
                bonusCardService.save(bonusCard);
                break;
            case EARN:
                transactionType = TransactionType.CREDIT;
                BigDecimal totalAmount = order.getTotalAmount();
                points = totalAmount.multiply(BigDecimal.valueOf(bonusCard.getDiscountPercent() / 100));
                bonusCard.setAmount(bonusCard.getAmount().add(points).setScale(2, RoundingMode.CEILING));
                bonusCardService.save(bonusCard);
                break;
            default:
                throw new IllegalStateException("Invalid bonus points action");
        }
        bonusCardTransactionBuilder
                .order(order)
                .transactionType(transactionType)
                .points(points)
                .build();
        clientBonusCardTransactionRepository.save(bonusCardTransactionBuilder.build());

        String message = order.getBonusPointsAction().equals(BonusPointsAction.USE)
                ? String.format("You have spent %s points. Your balance is %s", points, bonusCard.getAmount())
                : String.format("You have received %s points. Your balance is %s", points, bonusCard.getAmount());
        clientBonusNotificationService.notifyClient(message, order.getClient().getChatId());
    }
}
