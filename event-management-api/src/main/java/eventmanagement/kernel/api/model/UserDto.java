package eventmanagement.kernel.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends ResponseDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;

}
