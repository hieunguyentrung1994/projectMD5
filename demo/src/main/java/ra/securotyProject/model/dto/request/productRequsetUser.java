package ra.securotyProject.model.dto.request;

import ra.securotyProject.model.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class productRequsetUser {
    private Long Id;

    private String name;

    private double price;

    private String title;

    private String content;

    private String img;

    private Trademark trademark;

    private Users seller;

    private Set<OrderDetail> orderDetails;

    private List<Catagory> catagoris = new ArrayList<>();

    private List<Color> colors = new ArrayList<>();
}

