/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.UserLogin;
import bean.facade.TUserFacade;
import entity.TUser;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Windows 7
 */
@FacesValidator("userLoginValidator")
@ManagedBean
@RequestScoped
public class UserLoginValidator implements Validator
{
    @EJB
    private TUserFacade tUserFacade;
    @EJB
    private UserLogin userLogin;

    public UserLoginValidator()
    {
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        String mail = (String) value;
        UIInput passwordComponent = (UIInput) component.getAttributes().get("password");
        String password = (String)passwordComponent.getSubmittedValue();

        if (mail == null || mail.isEmpty())
        {
            FacesMessage message=new FacesMessage("Champs 'Mail' vide","Veuillez entrer votre adresse mail");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        if (password == null || password.isEmpty())
        {
            FacesMessage message=new FacesMessage("Champs 'Mot de passe' vide","Veuillez entrer votre mot de passe");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        
        List<TUser> users;
        users = this.tUserFacade.findAll();
        for(TUser user:users)
        {
            if(user.getMail().equals(mail))
            {
                if(this.userLogin.isBlocked())
                {
                    String timeToWait="";
                    int remainingTime=this.userLogin.getWaitTime();
                    if(remainingTime>=0)
                    {
                        if(remainingTime>0)
                        {
                            timeToWait=remainingTime+" minutes";
                        }
                        else if(remainingTime==0)
                        {
                            timeToWait="moins d'une minute";                        
                        }
                        FacesMessage message=new FacesMessage("Session bloquée",
                                "Votre session est bloquée.\nProchain essai dans: "+timeToWait);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);
                    }
                    else
                    {
                        this.userLogin.unLock();
                    }
                }
                MessageDigest md;
                try
                {
                    md = MessageDigest.getInstance("MD5");
                }
                catch (NoSuchAlgorithmException ex)
                {
                    Logger.getLogger(UserLoginValidator.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                md.update(password.getBytes(/*"UTF-8"*/));
                byte[] md5 = md.digest();
                String passwordEncrypted=new String(md5/*,"UTF-8"*/);
                
                if(!user.getPassword().equals(passwordEncrypted))
                {
                    this.userLogin.setLoginTry(this.userLogin.getLoginTry()+1);
                    if(this.userLogin.getLoginTry()>=3)
                    {
                        int blockTime=0;
                        if(this.userLogin.getLoginTry()==3)
                        {
                            blockTime=5;
                        }
                        else if(this.userLogin.getLoginTry()==4)
                        {
                            blockTime=10;
                        }
                        else if(this.userLogin.getLoginTry()>=5)
                        {
                            blockTime=15;                            
                        }
                        this.userLogin.sessionBlockFor(blockTime);
                        FacesMessage message=new FacesMessage("Session bloquée",
                                "Votre mot de passe est incorrect, "
                                + "votre session sera bloquée pendant "+blockTime+" minutes");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);
                    }
                    passwordComponent.setValid(false);
                    FacesMessage message=new FacesMessage("Erreur d'autentification",
                            "Votre mot de passe est incorrect");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
                
                this.userLogin.setLoginTry(0);
                this.userLogin.setUser(user);
                FacesMessage message=new FacesMessage("Autentification réussie",
                        "Bienvenue "+this.userLogin.getFirstname()+
                        " "+this.userLogin.getName());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null, message);
                return;
            }
        }
        FacesMessage message=new FacesMessage("Login inconnu",
                "Votre adresse mail n'a pas été reconnue");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}