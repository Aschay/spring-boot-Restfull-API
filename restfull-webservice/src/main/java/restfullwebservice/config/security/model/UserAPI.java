package restfullwebservice.config.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="usersapi",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
            })
       
    })	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAPI {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	
	@NotBlank
	@Size(min =5)
	@Pattern(regexp = "^[a-zA-Z0-9_-]+$")
	private String username;
	
	
	@NotBlank
	@Email
	@Pattern(regexp = "^(.+)@(.+)$")
	@Size(min =5)
	private String email;
	
	
	@NotBlank
    private String password;
	@NotBlank
	@Pattern(regexp = "^[A-Z_,]+$")
	private String roles ;
}
