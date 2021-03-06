package pl.archivizer.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import pl.archivizer.payload.response.simple.BasicResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleMapper<Response extends BasicResponse> {

    private final ModelMapper modelMapper;

    public SimpleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public <Response> Response mapToDTO(Object source, Class<Response> targetClass){
        return modelMapper.map(source, targetClass);
    }

    public <T> T map(Object source, Class<T> targetClass){
        return modelMapper.map(source, targetClass);
    }


    public void map(Object source, Object destination){
        modelMapper.map(source, destination);
    }

}
