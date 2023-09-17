package ra.securotyProject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.securotyProject.model.domain.*;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReponseSeller {
    private Long id;

    private String name;

    private double price;

    private String title;

    private String content;

    private String img;

    private Trademark trademark;

    private Users seller;

    private List<Catagory> catagoris = new ArrayList<>();

    private List<Color> colors = new ArrayList<>();

    private List<ImageProduct> images;
}
