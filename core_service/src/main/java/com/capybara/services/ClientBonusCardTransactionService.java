package com.capybara.services;

import com.capybara.entities.BonusCard;
import com.capybara.entities.ClientBonusCardTransaction;
import com.capybara.entities.Order;
import com.capybara.enums.TransactionType;
import com.capybara.repositories.ClientBonusCardTransactionRepository;
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
                points = totalAmount.multiply(BigDecimal.valueOf(bonusCard.getDiscountPercent()))
                        .divide(BigDecimal.valueOf(100)) ;
                bonusCard.setAmount(bonusCard.getAmount().add(points).setScale(2, RoundingMode.CEILING));
                bonusCardService.save(bonusCard);
                break;
            default:
                throw new IllegalStateException("Invalid bonus points action");
        }

        ClientBonusCardTransaction transaction = ClientBonusCardTransaction.builder()
                .order(order)
                .transactionType(transactionType)
                .points(points)
                .build();

        clientBonusCardTransactionRepository.save(transaction);

        clientBonusNotificationService.notifyClient(transaction, bonusCard);
    }
}
