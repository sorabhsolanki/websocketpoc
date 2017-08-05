package server.service;

import server.dto.Tournament;
import server.repository.TournamentRepository;

/**
 */
public class TournamentService {

  private final TournamentRepository tournamentRepository;

  public TournamentService(TournamentRepository tournamentRepository) {
    this.tournamentRepository = tournamentRepository;
  }


  public void saveTournament(Tournament tournament, String[] invitees){
    tournamentRepository.persist(tournament, invitees);
  }
}
