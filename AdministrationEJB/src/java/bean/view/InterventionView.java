/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view;

import bean.facade.InterventionFacade;
import bean.view.periodSelection.EmbeddedEntityPeriodView;
import entity.CUser;
import entity.Client;
import entity.Intervention;
import entity.Task;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "interventionView")
@SessionScoped
public class InterventionView extends EmbeddedEntityPeriodView<Task, Intervention, InterventionFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private InterventionFacade inteventionFacade;
    private Client restrictedClient=null;
    
    public InterventionView() throws NoSuchMethodException
    {
        super(Intervention.class,"intervention",
                Intervention.class.getMethod("setIdTask",
                                        new Class<?>[]{Task.class}));
    }

    public Client getRestrictedClient()
    {
        return restrictedClient;
    }

    public void setRestrictedClient(Client restrictedClient)
    {
        this.restrictedClient = restrictedClient;
    }
    
    public String entityCreateFromClient(TaskView view, Client client)
    {
        String result = this.entityCreate(view, true);
        this.getInstance().getIdTask().setIdClient(client);
        return result;
    }
    
    public String entityCreateFromUser(TaskView view, CUser user)
    {
        String result = this.entityCreateFromClient(view, user.getIdClient());
        this.getInstance().getIdTask().setIdUser(user);
        return result;
    }
    
    public String reactivate(Task task, Intervention entity)
    {
        entity.setIdFacture(null);
        entity.setSleeping(false);
        this.inteventionFacade.updateToDataList(task, entity);
        return "list";
    }
    
    public String entityCreate(TaskView view, boolean createParent)
    {
        if(createParent)
        {
            view.entityCreate();
        }
        else
        {
            view.entityView(view.getEntity());
        }
        view.setCreating(createParent);
        return super.entityCreate(view.getEntityPopup());
    }
    
    public String entityView(TaskView view, Intervention entity)
    {
        view.entityView(entity.getIdTask());
        return super.entityView(entity);
    }
    
    public String cancelCreate(TaskView view)
    {
        view.cancelCreate();
        return super.cancelCreate();
    }
    
    public String create(TaskView view)
    {
        if(view.isCreating())
        {
            view.getEntityPopup().setIntendedDuration(this.getInstance().getDuration());
            view.getEntityPopup().setStartDate(this.getInstance().getInterventionDate());
            view.create();
        }
        return super.create(view.getEntityPopup());
    }
    
    @Override
    public void setEntity(Intervention entity)
    {
        super.setInstance(entity);
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.inteventionFacade);
    }

    @Override
    public List<Intervention> getEntries()
    {
        List<Intervention> list=super.findAll();
        if(list!=null&&this.getStartDate()!=null
                &&this.getEndDate()!=null)
        {
            if(!super.verifDate(this.getStartDate(), this.getEndDate()))
            {
                return new ArrayList<Intervention>();
            }
            for(Intervention entity:super.findAll())
            {
                Date date=entity.getInterventionDate();
                if(date==null)
                {
                    continue;
                }
                boolean before=date.before(this.getStartDate());
                boolean after=date.after(this.getEndDate());
                boolean equals=this.getStartDate().toString().equals(
                                date.toString());
                if((before&&!equals)||after)
                {
                    list.remove(entity);
                    continue;
                }
                if(this.restrictedClient!=null&&
                        (!entity.getIdTask().getIdClient().equals(this.restrictedClient)||
                        entity.getSleeping()))
                {
                    list.remove(entity);
                    continue;
                }
            }
        }
        return list;
    }

    @Override
    public Intervention getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Intervention entity)
    {
        String details=(entity==null||entity.getIdTask()==null?"":" ("+entity.getIdTask().getDescription()
                    + " id="+entity.getId()+")");
        return "Vous êtes sur le point de supprimer définitivement"
                    + " cette intervention"+details+". Cette action est irreversible,"
                    + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public String deleteMessages(List<Intervention> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement tous"
                + " les utilisateurs sélectionnés (";
            boolean first=true;
            for(Intervention intervention:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=intervention.getIdTask().getDescription()+" id="+intervention.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }
}
