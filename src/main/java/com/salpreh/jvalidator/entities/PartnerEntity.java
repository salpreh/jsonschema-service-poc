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
@Table(name = "partner")
public class PartnerEntity {

  @Id
  @SequenceGenerator(name = "partner_id_seq", sequenceName = "partner_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "partner_id_seq", strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;
}
