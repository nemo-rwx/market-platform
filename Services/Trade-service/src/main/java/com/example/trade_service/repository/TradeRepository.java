package com.example.trade_service.repository;

import com.example.trade_service.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
