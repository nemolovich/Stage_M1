/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.struct;

import bean.facade.abstracts.AbstractFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EntityView<C,F extends AbstractFacade<C>> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private C entity;
    private Class<C> entityClass;
    private  String webFolder=null;
    private F entityFacade;
    private boolean creating=false;
    
    public EntityView()
    {
    }
    
    public EntityView(Class<C> entityClass,String webFolder)
    {
        this.webFolder="/restricted/admin/data/"+webFolder+"/";
        this.entityClass=entityClass;
    }
    
    public boolean isCreating()
    {
        return this.creating;
    }

    public void setCreating(boolean creating)
    {
        this.creating = creating;
    }
    
    protected void setWebFolder(String webFolder)
    {
        this.webFolder=webFolder;
    }
    
    public String create()
    {
        this.creating=false;
        this.setFacade();
        this.entityFacade.create(this.entity);
        return this.webFolder+"list";
    }
    
    public String delete()
    {
        this.creating=false;
        this.setFacade();
        this.entityFacade.remove(this.entity);
        return this.webFolder+"list";
    }
    
    public String update()
    {
        this.creating=false;
        this.setFacade();
        this.entityFacade.edit(this.entity);
        return this.webFolder+"list";
    }

    public String entityView(C entity)
    {
        this.creating = false;
        this.entity = entity;
        return this.webFolder+"view";
    }
    
    public C checkSingle(C[] entities)
    {
        if(entities!=null&&entities.length!=1)
        {
            FacesMessage message=new FacesMessage("Sélection invalide",
                    "Vous devez sélectionner un et un seul élément "
                    + "pour effectuer cette tâche");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        return entities[0];
    }
    
    public String entityUpdate(C entity)
    {
        this.creating = false;
        this.entity = entity;
        return this.webFolder+"update";
    }
    
    public String entityDelete(C entity)
    {
        this.creating = false;
        this.setFacade();
        this.entityFacade.remove(entity);
        return this.webFolder+"list";
    }
    
    public String entityCreate()
    {
        this.creating = true;
        String message="Création d'une entité de la classe '"+this.entityClass.getName()+"'";
        Logger.getLogger(EntityView.class.getName()).log(Level.INFO,
                message);
        try
        {
            this.entity = this.entityClass.newInstance();
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(EntityView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(EntityView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.webFolder+"create";
    }
    
    public String setInstance(C entity)
    {
        this.entity = entity;
        return null;
    }
    
    public C getInstance()
    {
        return this.entity;
    }

    public void setEntityFacade(F entityFacade)
    {
        this.entityFacade=entityFacade;
    }

    protected F getEntityFacade()
    {
        return this.entityFacade;
    }
    
    public List<C> findAll()
    {
        this.setFacade();
        List<C> result=this.entityFacade.findAll();
        if(result==null)
        {
            result=new ArrayList<C>();
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Les methodes abstraites">
    /**
     * Insérer le code:
     * <code>
     * super.entityFacade=this.[VotreEJBFacade];
     * </code>
     */
    public abstract void setFacade();
    /**
     * Insérer le code:
     * <code>
     * return super.findAll();
     * </code>
     */
    public abstract List<C> getEntries();
    /**
     * Insérer le code:
     * <code>
     * return super.getInstance();
     * </code>
     */
    public abstract C getEntity();
    /**
     * Insérer le code:
     * <code>
     * super.setInstance(C entity);
     * </code>
     * @param entity 
     */
    public abstract void setEntity(C entity);
    /**
     * Renvoi le message d'avertissement avant la suppression
     * de cette entité
     * @param entity {@link C} - L'entité à supprimer
     * @return {@link String}, Le message d'avertissement
     */
    public abstract String getDeleteMessage(C entity);
    
    // </editor-fold>
}