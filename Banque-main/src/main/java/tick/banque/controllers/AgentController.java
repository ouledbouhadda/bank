package tick.banque.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tick.banque.interfaces.*;
import tick.banque.models.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@RequestMapping("/agent")
@Controller
public class AgentController {

    @Autowired
    private CreditInterface creditInterface;
    @Autowired
    private AgentInterface agentInterface;
    @Autowired
    private OperationInterface operationRepository;
    @Autowired
    private CompteInterface compteRepository;
    @Autowired
    private ClientInterface clientRepository;
    @Autowired
    private PlacementInterface placementRepository;
    @Autowired
    private CreditInterface creditRepository;
    @Value("${app.upload.dir}")
    private String uploadDir;

    @GetMapping
    public String agentDashboard(Model model) {
        return "compte";
    }


    @GetMapping("/depot-retrait")
    public String showDepotRetraitForm() {
        return "depot-retrait";
    }


    @PostMapping("/depot-retrait")
    public String depotRetrait(@RequestParam String numCpt, @RequestParam String operationType, @RequestParam BigDecimal montant, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Agent loggedAgent = (Agent) authentication.getPrincipal();
        int agentId = loggedAgent.getAgentId();
        Compte compte = compteRepository.findById(Integer.parseInt(numCpt)).orElse(null);
        if (compte == null) {
            model.addAttribute("error", "Compte not found");
            return "depot-retrait";
        }
        Operation operation;
        switch (operationType.toUpperCase()) {
            case "DEPOT":
                operation = new Versement(0,LocalDateTime.now(), agentId, BigDecimal.ZERO, montant);
                compte.setMontantGlobal(compte.getMontantGlobal().add(montant));
                break;
            case "RETRAIT":
                if (compte.getMontantGlobal().compareTo(montant) >= 0) {
                    operation = new Retrait(0, LocalDateTime.now(), agentId, BigDecimal.ZERO, montant);
                    compte.setMontantGlobal(compte.getMontantGlobal().subtract(montant));
                } else {
                    model.addAttribute("error", "Insufficient funds");
                    return "depot-retrait";
                }
                break;
            default:
                model.addAttribute("error", "Invalid operation type");
                return "depot-retrait";
        }
        operation.setCompte(compte);
        operationRepository.save(operation);
        compteRepository.save(compte);
        model.addAttribute("success", "Operation processed successfully");
        return "depot-retrait";
    }


    @GetMapping("/virement")
    public String showVirement(Model model) {
        return "virement";
    }


    @PostMapping("/virement")
    public String virement(@RequestParam int numCptD,@RequestParam int numCptC,@RequestParam BigDecimal  montant,Model model) {
        Compte compteDebiteur = compteRepository.findById(numCptD).orElse(null);
        if (compteDebiteur == null) {
            model.addAttribute("error", "Debiting account not found");
            return "virement";
        }
        Compte compteCrediteur = compteRepository.findById(numCptC).orElse(null);
        if (compteCrediteur == null) {
            model.addAttribute("error", "Crediting account not found");
            return "virement";
        }
        if (compteDebiteur.getMontantGlobal().compareTo(montant) < 0) {
            model.addAttribute("error", "Insufficient funds in debiting account");
            return "virement";
        }
        compteDebiteur.setMontantGlobal(compteDebiteur.getMontantGlobal().subtract(montant));
        compteCrediteur.setMontantGlobal(compteCrediteur.getMontantGlobal().add(montant));
        compteRepository.save(compteDebiteur);
        compteRepository.save(compteCrediteur);
        model.addAttribute("success", "Transfer successful");
        return "virement";
    }


    @GetMapping("/creerCompte")
    public String showCreerCompte(Model model) {
        model.addAttribute("client", new Client());
        return "creerCompte";
    }


    @PostMapping("/creerCompte")
    public String creerCompte(@ModelAttribute("client") Client client, @RequestParam("montant") BigDecimal montantInitial, Model model) {
        try {
            clientRepository.save(client);
            Compte newAccount = new Compte(0, montantInitial, montantInitial, client.getCodeCli());
            compteRepository.save(newAccount);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "An account with the provided details already exists or some constraints are violated.");
            return "creerCompte";
        } catch (TransactionSystemException e) {
            model.addAttribute("error", "We encountered a problem while processing your request. Please try again.");
            return "creerCompte";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "creerCompte";
        }
        model.addAttribute("success", "Account created successfully!");
        return "creerCompte";
    }


    @GetMapping("/supprimer")
    public String showSupprimerCompte(Model model) {
        return "supprimer";
    }


    @PostMapping("/supprimer")
    public String supprimerCompte(@RequestParam("numCpt") int numCpt, Model model) {
        Optional<Compte> compte = compteRepository.findById(numCpt);
        if (!compte.isPresent()) {
            model.addAttribute("error", "Compte not found");
            return "supprimer";
        }
        if (compte.get().getMontantGlobal().compareTo(BigDecimal.ZERO) != 0) {
            model.addAttribute("error", "Account must have a zero balance before it can be closed");
            return "supprimer";
        }
        compteRepository.delete(compte.get());
        model.addAttribute("success", "Account successfully deleted");
        return "supprimer";
    }


    @GetMapping("/placement")
    public String showPlacement(Model model) {
        return "placement";
    }

    @PostMapping("/placement")
    public String placement(@RequestParam("numCpt") int numCpt, @RequestParam("montant") BigDecimal montant, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Agent loggedAgent = (Agent) authentication.getPrincipal();
        int agentId = loggedAgent.getAgentId();
        Compte compte = compteRepository.findById(numCpt).orElse(null);
        if (compte == null) {
            model.addAttribute("error", "Compte non trouvé.");
            return "placement";
        }
        if (compte.getMontantGlobal().compareTo(montant) < 0) {
            model.addAttribute("error", "Fonds insuffisants pour le placement.");
            return "placement";
        }
        compte.setMontantGlobal(compte.getMontantGlobal().subtract(montant));
        compteRepository.save(compte);
        Placement placement = new Placement(0, LocalDateTime.now(), agentId, BigDecimal.ZERO, montant, BigDecimal.ZERO, montant.multiply(new BigDecimal("1.07")));
        placement.setCompte(compte);
        placementRepository.save(placement);
        model.addAttribute("success", "Placement créé avec succès. Le montant sera ajouté avec intérêt après la période définie.");
        return "placement";
    }


    @GetMapping("/demandeCredit")
    public String showDemandeCredit(Model model) {
        model.addAttribute("credit", new Credit());
        return "demandeCredit";
    }

    @PostMapping("/demandeCredit")
    public String demandeCredit(@ModelAttribute("credit") Credit credit,@RequestParam("file") MultipartFile file, BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Validation errors!");
            return "demandeCredit";
        }
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload");
            return "redirect:demandeCredit";
        } else if (!file.getContentType().equals("application/pdf")) {
            model.addAttribute("error", "Only PDF files are allowed");
            return "redirect:demandeCredit";
        }
        try {
            Path path = Paths.get(uploadDir, file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
            credit.setDateCre(LocalDateTime.now());
            credit.setStatus("En attente");
            credit.setFilesPath(file.getOriginalFilename());
            creditRepository.save(credit);
            model.addAttribute("success", "Credit application submitted successfully!");
        } catch (IOException e) {
            model.addAttribute("error", "Failed to save file: " + e.getMessage());
            return "demandeCredit";
        }

        return "compte";
    }

    @GetMapping("/credit")
    public String creditPage(Model model) {
        return "paiementCredit";
    }

    @PostMapping("/paiementCredit")
    public String paiementCredit(@RequestParam("creditNumber") int creditNumber,
                                 @RequestParam("paymentAmount") BigDecimal paymentAmount,
                                 Model model) {
        Credit credit = creditInterface.findById(creditNumber).orElse(null);
        if (credit != null && paymentAmount.compareTo(BigDecimal.ZERO) > 0) {
            if (credit.getMontCre().compareTo(paymentAmount) >= 0) {
                credit.setMontCre(credit.getMontCre().subtract(paymentAmount));
                creditInterface.save(credit);
                model.addAttribute("message", "Payment successful. Remaining credit amount: " + credit.getMontCre());
            } else {
                model.addAttribute("message", "Payment amount exceeds the credit amount.");
            }
        } else {
            model.addAttribute("message", "Invalid credit number or payment amount.");
        }
        return "paiementCredit";
    }

}
