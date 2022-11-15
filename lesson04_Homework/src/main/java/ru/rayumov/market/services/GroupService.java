package ru.rayumov.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.entities.GroupEntity;
import ru.rayumov.market.repositories.GroupRepository;
import ru.rayumov.market.soap.groups.Group;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public static final Function<GroupEntity, Group> functionEntityToSoap = ge -> {
        Group g = new Group();
        g.setTitle(ge.getTitle());
        ge.getStudents().stream().map(StudentService.functionEntityToSoap).forEach(s -> g.getStudents().add(s));
        return g;
    };

    public Group getByTitle(String title) {
        return groupRepository.findByTitle(title).map(functionEntityToSoap).get();
    }
}
