package lib.penalty;

import lib.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledPenaltyUpdaterService {

    private PenaltyService penaltyService;
    private UserRepository userRepository;

    @Autowired
    public ScheduledPenaltyUpdaterService(PenaltyService penaltyService,
                                          UserRepository userRepository) {
        this.penaltyService = penaltyService;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void updatePenalties() {
        penaltyService.updatePenalties(userRepository.findAll());
    }
}
