package com.hnaqvi.springreact.dbo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "products")
@EqualsAndHashCode(of = "id")
@Entity
public class ProductDBO {

	/** Auditing
	 * For auditing spring provides an easy way to configure an update audit fields automatically. As I am reading the dates form file and populating the db the application (InitialDataLoader) using JPA repo, ...
	 * ...I am not able to use this future as spring overrides these dates. Better solution is to either dont provide these dates initially OR directly populate the data with cli beforehand
	 * see CategoryDBO for a nice example of automated configuration of audit fields
	 */

	@Id
	private Long id;

	private String name;

	private String description;

	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private CategoryDBO category;

	@Column(nullable = false)
	private LocalDateTime creationDate;

	@Column(nullable = false)
	private LocalDateTime updateDate;

	private LocalDateTime lastPurchasedDate;
}
