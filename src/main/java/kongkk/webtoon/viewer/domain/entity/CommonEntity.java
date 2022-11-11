package kongkk.webtoon.viewer.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class CommonEntity {
	
	@Column
	@CreationTimestamp
	private LocalDateTime createAt;
	
	@Column
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
