package Order_management.leads;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LeadsService {
    private final LeadsRepository leadsRepository;


}
