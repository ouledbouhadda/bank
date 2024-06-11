package tick.banque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tick.banque.models.Agent;
import tick.banque.interfaces.AgentInterface;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AgentInterface agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Agent agent = agentRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));

        return agent;  // Since Agent implements UserDetails, you can directly return it
    }
}
