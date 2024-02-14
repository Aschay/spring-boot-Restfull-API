package restfullwebservice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	UUID id;
    
	@NotBlank
	@Size(min = 3, max = 25)
	@Pattern(regexp = "^[a-zA-Z]+$")
	String firstName;
    @NotBlank
	@Size(min = 3, max = 25)
	@Pattern(regexp = "^[a-zA-Z]+$")
	String lastName;

	@Size(min = 3, max = 25)
	@Pattern(regexp = "^[a-zA-Z0-9_-]+$")
	String username;

	@Email
	String email;

	@CreatedDate
	private LocalDateTime dateCreated;
   
	
	

}
