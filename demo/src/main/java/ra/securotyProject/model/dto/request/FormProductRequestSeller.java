package ra.securotyProject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.securotyProject.model.domain.*;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormProductRequestSeller {

    private String name;

    private double price;

    private String title;

    private String content;

    private String img;

    private Long trademark;

    private Users seller;

    private List<Long> catagoris = new ArrayList<>();

    private List<Long> colors = new ArrayList<>();

    private List<MultipartFile> file;
}
