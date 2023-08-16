package com.salpreh.jvalidator.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Entity
public class ContractEntity {

  @Id
  @GeneratedValue(generator = "contract_id_seq", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "contract_id_seq", sequenceName = "contract_id_seq", allocationSize = 1)
  private Long id;

  private OffsetDateTime validFrom;

  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> data;

  @ManyToOne(cascade = {CascadeType.REFRESH})
  @JoinColumn(name = "contract_type_id")
  private ContractTypeEntity contractType;
}
