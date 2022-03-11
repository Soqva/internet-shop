package com.s0qva.application.service;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.exception.NoSuchOrderException;
import com.s0qva.application.exception.model.enumeration.DefaultExceptionMessage;
import com.s0qva.application.mapper.order.GeneralOrderMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(
        MockitoExtension.class
)
class OrderServiceTest {
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GeneralOrderMapper generalOrderMapper;

    @BeforeEach
    void setUp() {
        this.orderService = new OrderService(orderRepository, generalOrderMapper);
    }

    @Test
    void itShouldReturnAllRandomOrdersAsOrderReadingDtoList() {
        List<Order> ordersBeforeMapping = List.of(
                new Order(),
                new Order(),
                new Order()
        );
        List<OrderReadingDto> expectedOrderReadingDtoList = List.of(
                new OrderReadingDto(),
                new OrderReadingDto(),
                new OrderReadingDto()
        );

        when(orderRepository.findAll())
                .thenReturn(ordersBeforeMapping);
        when(generalOrderMapper.mapOrderToOrderReadingDto(any(Order.class)))
                .thenReturn(new OrderReadingDto());

        List<OrderReadingDto> actualOrderReadingDtoList = orderService.getAllOrders();

        assertAll(() -> {
            assertThat(actualOrderReadingDtoList.size())
                    .isEqualTo(expectedOrderReadingDtoList.size());
            assertThat(actualOrderReadingDtoList)
                    .isEqualTo(expectedOrderReadingDtoList);
        });

        verify(orderRepository, times(1))
                .findAll();
        verify(generalOrderMapper, times(expectedOrderReadingDtoList.size()))
                .mapOrderToOrderReadingDto(any(Order.class));
    }

    @Test
    void itShouldReturnOneOrderAsOrderReadingDtoByItsId() {
        Long orderId = OrderTestValue.EXISTING_ORDER_ID;
        Order orderBeforeMapping = Order.builder()
                .id(orderId)
                .build();
        OrderReadingDto expectedOrderReadingDto = OrderReadingDto.builder()
                .id(orderId)
                .build();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(orderBeforeMapping));
        when(generalOrderMapper.mapOrderToOrderReadingDto(orderBeforeMapping))
                .thenReturn(expectedOrderReadingDto);

        OrderReadingDto actualOrderReadingDto = orderService.getOrder(orderId);

        assertThat(actualOrderReadingDto)
                .isEqualTo(expectedOrderReadingDto);

        verify(orderRepository, times(1))
                .findById(orderId);
        verify(generalOrderMapper, times(1))
                .mapOrderToOrderReadingDto(orderBeforeMapping);
    }

    @Test
    void itShouldThrowsNoSuchOrderExceptionWithSpecificMessageWhenOrderDoesntExistWithSentId() {
        Long nonExistentOrderId = OrderTestValue.NON_EXISTENT_ORDER_ID;
        Optional<Order> nonExistentOrder = Optional.empty();
        String expectedExceptionMessage = DefaultExceptionMessage.NO_SUCH_ORDER_WITH_ID.getMessage() + nonExistentOrderId;

        when(orderRepository.findById(nonExistentOrderId))
                .thenReturn(nonExistentOrder);

        NoSuchOrderException actualException = assertThrows(NoSuchOrderException.class,
                () -> orderService.getOrder(nonExistentOrderId));

        assertThat(actualException)
                .isOfAnyClassIn(NoSuchOrderException.class)
                .hasMessage(expectedExceptionMessage);

        verify(orderRepository, times(1))
                .findById(nonExistentOrderId);
    }

    @Test
    void itShouldReturnSavedOrderIdWhenSendOrderCreationDto() {
        Long savedOrderId = OrderTestValue.EXISTING_ORDER_ID;
        OrderCreationDto orderCreationDto = OrderCreationDto.builder()
                .userId(OrderTestValue.EXISTING_USER_ID_DTO)
                .build();
        Order unsavedOrderAfterMapping = Order.builder()
                .build();
        Order savedOrder = Order.builder()
                .id(savedOrderId)
                .build();
        OrderIdDto expectedProductIdDto = OrderIdDto.builder()
                .id(savedOrderId)
                .build();

        when(generalOrderMapper.mapOrderCreationDtoToOrder(orderCreationDto))
                .thenReturn(unsavedOrderAfterMapping);
        when(orderRepository.save(unsavedOrderAfterMapping))
                .thenReturn(savedOrder);
        when(generalOrderMapper.mapOrderToOrderIdDto(savedOrder))
                .thenReturn(expectedProductIdDto);

        OrderIdDto actualOrderIdDto = orderService.saveOrder(orderCreationDto);

        assertThat(actualOrderIdDto)
                .isEqualTo(expectedProductIdDto);

        verify(generalOrderMapper, times(1))
                .mapOrderCreationDtoToOrder(orderCreationDto);
        verify(orderRepository, times(1))
                .save(unsavedOrderAfterMapping);
        verify(generalOrderMapper, times(1))
                .mapOrderToOrderIdDto(savedOrder);
    }

    @Test
    void itShouldDeleteOrderByItsId() {
        Long orderId = OrderTestValue.EXISTING_ORDER_ID;
        Order order = Order.builder()
                .id(orderId)
                .build();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));
        doNothing()
                .when(orderRepository)
                .delete(order);

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1))
                .findById(orderId);
        verify(orderRepository, times(1))
                .delete(order);
    }

    private static class OrderTestValue {
        private static final Long EXISTING_ORDER_ID = 1L;
        private static final Long NON_EXISTENT_ORDER_ID = -1L;
        private static final Long EXISTING_USER_ID = 1L;
        private static final UserIdDto EXISTING_USER_ID_DTO = new UserIdDto(EXISTING_USER_ID);
    }
}
