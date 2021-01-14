package com.kdgc.flow.dao;

import com.kdgc.flow.entity.DcwebFlows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcFlowDao extends JpaRepository<DcwebFlows, Long> {

    public DcwebFlows findByFlowCode(String flowCode);
}
