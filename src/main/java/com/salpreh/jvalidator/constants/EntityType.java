package com.salpreh.jvalidator.constants;

import com.salpreh.jvalidator.entities.PartnerEntity;
import com.salpreh.jvalidator.entities.SalesmanAgentEntity;

public enum EntityType {

  SALES_AGENT(SalesmanAgentEntity.class),
  PARTNER(PartnerEntity.class);

  private Class<?> entityClass;

  EntityType(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  public Class<?> getEntityClass() {
    return entityClass;
  }
}
