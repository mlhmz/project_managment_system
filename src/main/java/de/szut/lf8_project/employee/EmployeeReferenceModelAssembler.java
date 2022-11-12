package de.szut.lf8_project.employee;

import de.szut.lf8_project.project.entities.ProjectEmployee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.ArrayList;
import java.util.List;

public class EmployeeReferenceModelAssembler implements RepresentationModelAssembler<ProjectEmployee, EmployeeReferenceDto> {
    @Override
    public EmployeeReferenceDto toModel(ProjectEmployee entity) {
        EmployeeReferenceDto dto = new EmployeeReferenceDto(entity.getEmployeeId());
        Link selfLink = Link.of("https://employee.szut.dev/employees/" + dto.getId());
        dto.add(selfLink);
        return dto;
    }

    @Override
    public CollectionModel<EmployeeReferenceDto> toCollectionModel(Iterable<? extends ProjectEmployee> entities) {
        List<EmployeeReferenceDto> dtoList = new ArrayList<>();

        for (ProjectEmployee entity : entities) {
            EmployeeReferenceDto dto = new EmployeeReferenceDto(entity.getEmployeeId());
            Link selfLink = Link.of("https://employee.szut.dev/employees/" + dto.getId());
            dto.add(selfLink);
            dtoList.add(dto);
        }

        return CollectionModel.of(dtoList);

    }
}
