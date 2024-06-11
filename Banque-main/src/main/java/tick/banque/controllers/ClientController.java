package tick.banque.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tick.banque.interfaces.AgentInterface;
import tick.banque.interfaces.ClientInterface;
import tick.banque.interfaces.CompteInterface;
import tick.banque.interfaces.OperationInterface;
import tick.banque.models.Agent;
import tick.banque.models.Client;
import tick.banque.models.Compte;
import tick.banque.models.Operation;

import java.util.List;


@RequestMapping("/client")
@Controller
public class ClientController {
    @Autowired
    private ClientInterface client;
    @Autowired
    private AgentInterface agentInterface;
    @Autowired
    private ClientInterface clientInterface;
    @Autowired
    private CompteInterface compteInterface;
    @Autowired
    private OperationInterface operationInterface;
    // Get all examples
    @GetMapping
    public String clientPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Assuming the agent is linked to the client
        Agent agent = agentInterface.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Client clientData = clientInterface.findById(agent.getAgentId()).orElseThrow(() -> new IllegalArgumentException("Client details not found"));

        // Fetch the account details using the client's ID
       Compte compteData = compteInterface.findByCodeCli(clientData.getCodeCli()).orElseThrow(() -> new IllegalArgumentException("Account details not found"));
        List<Operation> operations = operationInterface.findByCompte(compteData);
        model.addAttribute("client", clientData);
        model.addAttribute("compte", compteData);
        model.addAttribute("operations", operations);
        return "client";
    }

}
