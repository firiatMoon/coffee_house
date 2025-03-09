package com.capybara.services;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
//    private final UserService userService;
//    private final ClientService clientService;
//    private final OrderRepository orderRepository;
//    private final MenuService menuService;
//    private final OrderItemRepository orderItemRepository;
//    private final ClientBonusCardTransactionService clientBonusCardTransactionService;
//
//    @Autowired
//    public OrderService(UserService userService, ClientService clientService, OrderRepository orderRepository, MenuService menuService, OrderItemRepository orderItemRepository, ClientBonusCardTransactionService clientBonusCardTransactionService) {
//        this.userService = userService;
//        this.clientService = clientService;
//        this.orderRepository = orderRepository;
//        this.menuService = menuService;
//        this.orderItemRepository = orderItemRepository;
//        this.clientBonusCardTransactionService = clientBonusCardTransactionService;
//    }
//
//    @Transactional
//    public void saveOrder(OrderFormDto orderFormDto) {
//        User user = userService.convertFromDto(orderFormDto.getUser());
//
//        Client client = Optional.ofNullable(orderFormDto.getClient())
//                .map(clientService::convertFromDto)
//                .orElse(null);
//
//        Order order = Order.builder()
//                .user(user)
//                .client(client)
//                .bonusPointsAction(orderFormDto.getBonusPointsAction())
//                .totalAmount(orderFormDto.getTotalAmount())
//                .discountAmount(orderFormDto.getDiscountAmount())
//                .finalAmount(orderFormDto.getFinalAmount())
//                .build();
//
//        Order placedOrder = orderRepository.save(order);
//
//        List<Long> menuItems = orderFormDto.getItems().stream()
//                .map(OrderFormItemDto::getMenuItemId)
//                .toList();
//
//        Map<Long, Menu> mapMenuItems = menuService.findMenuByIdIn(menuItems).stream()
//                .collect(Collectors.toMap(Menu::getId, Function.identity()));
//
//        Set<OrderItem> orderItems = orderFormDto.getItems().stream()
//                .map(orderFormItemDto -> {
//                    Menu menu = mapMenuItems.get(orderFormItemDto.getMenuItemId());
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setOrder(placedOrder);
//                    orderItem.setMenu(menu);
//                    orderItem.setQuantity(orderFormItemDto.getQuantity());
//                    orderItem.setPrice(menu.getPrice());
//                    return orderItem;
//                }).collect(Collectors.toSet());
//
//        orderItemRepository.saveAll(orderItems);
//
//        placedOrder.setOrderItems(orderItems);
//
//        orderRepository.save(placedOrder);
//
//        if (client != null) {
//            clientBonusCardTransactionService.createTransactionFromOrder(placedOrder);
//        }
//    }
}
