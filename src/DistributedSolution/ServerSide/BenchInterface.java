package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;

/**
 * Created by Andre on 11/04/2016.
 */
public class BenchInterface {

    private Bench bench;


    public BenchInterface(Bench bench){

        this.bench = bench;

    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

     /* validação da mensagem recebida */

        switch (inMessage.getType ())
        { case Message.SETNFIC:  if ((inMessage.getFName () == null) || (inMessage.getFName ().equals ("")))
            throw new MessageException ("Nome do ficheiro inexistente!", inMessage);
            break;
            case Message.REQCUTH:  if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= bShop.getNCust ()))
                throw new MessageException ("Id do cliente inválido!", inMessage);
                break;
            case Message.ENDOP:
            case Message.GOTOSLP:
            case Message.CALLCUST: if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= bShop.getNBarb ()))
                throw new MessageException ("Id do barbeiro inválido!", inMessage);
                break;
            case Message.GETPAY:   if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= bShop.getNBarb ()))
                throw new MessageException ("Id do barbeiro inválido!", inMessage);
                if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= bShop.getNCust ()))
                    throw new MessageException ("Id do cliente inválido!", inMessage);
                break;
            default:               throw new MessageException ("Tipo inválido!", inMessage);
        }

     /* seu processamento */

        switch (inMessage.getType ())

        { case Message.SETNFIC:                                                     // inicializar ficheiro de logging
            bShop.setFileName (inMessage.getFName (), inMessage.getNIter ());
            outMessage = new Message (Message.NFICDONE);       // gerar resposta
            break;

            case Message.REQCUTH:  if (bShop.goCutHair (inMessage.getCustId ()))      // o cliente quer cortar o cabelo
                outMessage = new Message (Message.CUTHDONE);    // gerar resposta positiva
            else outMessage = new Message (Message.BSHOPF); // gerar resposta negativa
                break;
            case Message.GOTOSLP:  if (bShop.goToSleep (inMessage.getBarbId ()))      // o barbeiro vai dormir
                outMessage = new Message (Message.END);         // gerar resposta positiva
            else outMessage = new Message (Message.CONT);   // gerar resposta negativa
                break;
            case Message.CALLCUST: int custID = bShop.callCustomer (inMessage.getBarbId ());  // chamar cliente
                outMessage = new Message (Message.CUSTID, custID); // enviar id do cliente
                break;
            case Message.GETPAY:                                                      // receber pagamento
                bShop.getPayment (inMessage.getBarbId (), inMessage.getCustId ());
                outMessage = new Message (Message.ACK);            // gerar confirmação
                break;
            case Message.ENDOP:                                                       // fim de operações do barbeiro
                bShop.endOperation (inMessage.getBarbId ());
                outMessage = new Message (Message.ACK);            // gerar confirmação
                break;
        }

        return (outMessage);
    }
    }

}
