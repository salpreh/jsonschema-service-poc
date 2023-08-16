package com.salpreh.jvalidator.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "salesman_agent")
public class SalesmanAgentEntity {
  @Id
  @SequenceGenerator(name = "salesman_agent_id_seq", sequenceName = "salesman_agent_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "salesman_agent_id_seq", strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;

  private String email;

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof SalesmanAgentEntity)) return false;

      return id != null && id.equals(((SalesmanAgentEntity) o).getId());
  }

  @Override
  public int hashCode() {
      return getClass().hashCode();
  }
}
