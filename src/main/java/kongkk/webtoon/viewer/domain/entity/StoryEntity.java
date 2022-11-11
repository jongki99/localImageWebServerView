package kongkk.webtoon.viewer.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stories")
public class StoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private WriterEntity writer;
	
	@ManyToOne
	private SaveLocationEntity location;
	
	@OneToMany(mappedBy = "story")
	private List<BookEntity> books;
	
	@Column
	private String name;
	
	@Column
	private Integer sequence;
	
	@Column
	@CreationTimestamp
	private LocalDateTime createAt;
	
	@Column
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
