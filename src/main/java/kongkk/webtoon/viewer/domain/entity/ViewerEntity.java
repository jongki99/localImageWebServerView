package kongkk.webtoon.viewer.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "viewers")
public class ViewerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String userId;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "viewer")
	private List<FavoriteEntity> favorites;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "visitor")
	private List<ViewHistoryEntity> viewHistories;

	@Column
	@CreationTimestamp
	private LocalDateTime createAt;
}
