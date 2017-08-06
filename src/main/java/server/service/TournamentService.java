package server.service;

import java.util.List;
import server.dto.Tournament;
import server.repository.TournamentRepository;

/**
 */
public class TournamentService {

  private static volatile TournamentService tournamentService;
  private final TournamentRepository tournamentRepository;

  private TournamentService(TournamentRepository tournamentRepository) {
    this.tournamentRepository = tournamentRepository;
  }

  public static void init(TournamentRepository tournamentRepository){
    if(tournamentService == null){
      synchronized (TournamentService.class){
        if(tournamentService == null){
          tournamentService = new TournamentService(tournamentRepository);
        }
      }
    }
  }

  public static TournamentService getInstance(){
    return tournamentService;
  }

  public void saveTournament(Tournament tournament, String[] invitees){
    tournamentRepository.persist(tournament, invitees);
  }

  public List<Tournament> getTournamentAccessDetails(String userName){
    return tournamentRepository.getAccessDetails(userName);
  }
}
