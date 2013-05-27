/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.facade.CUserFacade;
import entity.CUser;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Windows 7
 */
@FacesValidator("cUserUniqueFieldValidator")
public class CUserUniqueFieldValidator extends UniqueFieldValidator<CUser, CUserFacade>implements Validator
{
    @EJB
    private CUserFacade cUserFacade;

    public CUserUniqueFieldValidator()
    {
        super(CUser.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.cUserFacade;
        super.validate(context, component, value);
    }
}