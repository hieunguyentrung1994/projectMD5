package ra.securotyProject.service.Mapper;

import org.springframework.stereotype.Component;
import ra.securotyProject.model.domain.Color;
import ra.securotyProject.model.dto.request.ColorRequest;
import ra.securotyProject.model.dto.response.ColorResponse;
import ra.securotyProject.service.IGenericMapper;
import ra.securotyProject.service.IGenericService;
@Component
public class ColorMapper implements IGenericMapper<Color, ColorRequest, ColorResponse> {
    @Override
    public Color toEntity(ColorRequest colorRequest) {
        return Color.builder().nameColor(colorRequest.getColorName()).build();
    }

    @Override
    public ColorResponse toResponse(Color color) {
        return ColorResponse.builder().colorName(color.getNameColor()).id(color.getId()).build();
    }
}
