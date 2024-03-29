package kongkk.webtoon.viewer.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class BookEntity extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private StoryEntity story;
	
	@OneToMany(mappedBy = "book")
	private List<ViewHistoryEntity> viewHistories;
	
	@Column
	private String name;
	
	@Column
	private Integer sequence;
}
