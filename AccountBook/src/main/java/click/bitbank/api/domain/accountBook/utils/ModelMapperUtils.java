package click.bitbank.api.domain.accountBook.utils;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}
