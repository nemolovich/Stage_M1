/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.ApplicationLogger.displayError;
import entity.FilePath;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "utils")
@ApplicationScoped
public class Utils
{
    /**
     * Nom de l'application
     */
    public static final String APPLICATION_NAME="Administration";
    /**
     * Répertoire des fichier uploadés sur le serveur
     */
    private static final String UPLOADS_PATH="C:\\temp\\uploads\\";
    /**
     * Le nombre maximum de ligne dans une liste de données
     */
    private final int maxDataRows = 10;
    /**
     * Droits de l'application
     */
    public static final String UNKNOWN_RIGHTS = "UNKNOWN";
    public static final String USER_RIGHTS = "USER";
    public static final String ADMIN_RIGHTS = "ADMIN";
    private static final List<String> RIGHTS = Arrays.asList(
            Utils.UNKNOWN_RIGHTS,
            Utils.USER_RIGHTS,
            Utils.ADMIN_RIGHTS);
    /**
     * Formats de date en francais
     */
    private static final DateFormat formatFull = new SimpleDateFormat(
            "EEEEE dd MMMMM yyyy à HH:mm:ss",
            Locale.FRANCE);
    private static final SimpleDateFormat formatMedium = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final SimpleDateFormat formatSmall = new SimpleDateFormat("dd/MM/yyyy");

    public Utils()
    {
    }
    
    /**
     * Renvoi le contexte courant de l'application
     * @return {@link ServletContext} - Contexte courant
     */
    public static ServletContext getServletContext()
    {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }
    
    /**
     * Renvoi le répertoire courant de l'application
     * @return {@link String} - Répertoire courant
     */
    public static String getRealPath()
    {
        return Utils.getServletContext().getRealPath("") + File.separator;
    }
    
    /**
     * Renvopi le répertoire où sont stoqués les fichier uploadés sur le serveur
     * @return {@link String} - Répertoire des téléchargements
     */
    public static String getUploadsPath()
    {
        return Utils.UPLOADS_PATH;
    }
    
    /**
     * Renvoi la liste des extensions autorisées pour les fichiers texte joints
     * @return {@link String} - Liste des extensions
     */
    public static String getTextFilesExtensions()
    {
        return "/(\\.|\\/)("+FilePath.TEXT_FILES_EXTENSIONS
                +"|"+FilePath.TEXT_FILES_EXTENSIONS.toUpperCase()
                +")$/";
    }
    
    /**
     * Renvoi les droits connus par l'application
     * @return {@link List}<{@link String}> - Liste des droits
     */
    public static List<String> getEnumRights()
    {
        return Utils.RIGHTS;
    }
    
    /**
     * Renvoi la date en chaîne de caractères en français
     * @param date {@link java.util.Date} - La date à afficher
     * @return {@link String} - La date en texte [EEEEE dd MMMMM yyyy à HH:mm:ss]
     */
    public static String fullDateFormat(Date date)
    {
        return formatFull.format(date);
    }
    
    /**
     * Renvoi la date en chaîne de caractères en français
     * @param date {@link java.util.Date} - La date à afficher
     * @return {@link String} - La date en texte [dd/MM/yyyy HH:mm:ss]
     */
    public static String dateFormat(Date date)
    {
        return formatMedium.format(date);
    }
    
    /**
     * Renvoi la date en chaîne de caractères en français
     * @param date {@link java.util.Date} - La date à afficher
     * @return {@link String} - La date en texte [dd/MM/yyyy]
     */
    public static String smallDateFormat(Date date)
    {
        return formatSmall.format(date);
    }
    
    /**
     * Parse une date suivant le format donné et renvoi la date trouvée
     * @param dateString {@link String} - Date en format texte
     * @param format {@link DateFormat} - Le format à utiliser pour parser
     * @return {@link Date} - La date déduite du parsing
     */
    private static Date dateParseFormat(String dateString, DateFormat format)
    {
        try
        {
            return format.parse(dateString);
        }
        catch (ParseException ex)
        {
            displayError("Impossible de parser la date '"+dateString+"'", null);
            return null;
        }
    }
    
    /**
     * Renvoi une date depuis un format texte complet
     * @param dateString {@link String} - La date à parser
     * @return {@link Date} - La date trouvée
     */
    public static Date parseFullDate(String dateString)
    {
        return dateParseFormat(dateString, formatFull);
    }
    
    /**
     * Renvoi une date depuis un format texte
     * @param dateString {@link String} - La date à parser
     * @return {@link Date} - La date trouvée
     */
    public static Date parseDate(String dateString)
    {
        return dateParseFormat(dateString, formatMedium);
    }
    
    /**
     * Renvoi une date depuis un format texte réduit
     * @param dateString {@link String} - La date à parser
     * @return {@link Date} - La date trouvée
     */
    public static Date parseSmallDate(String dateString)
    {
        return dateParseFormat(dateString, formatSmall);
    }
    
    /**
     * Renvoi le nombre max de lignes dans une liste de données
     * @return {@link Integer int} Nombre de lignes
     */
    public int getMaxDataRows()
    {
        return this.maxDataRows;
    }
    
    /**
     * Remplace chaque caractère d'un texte par une étoile
     * @param text {@link String} - Le texte à convertir
     * @return {@link String} Le texte contenant les étoiles
     */
    public static String getHiddenString(String text)
    {
        return text.replaceAll(".", "•");
    }
    
    /**
     * Raccourci un texte vers la taille donnée
     * @param text {@link String} - Le texte à raccourcir
     * @param maxSize {@link Integer int} - La taille max
     * @return {@link String} Le texte plus court
     */
    public static String getShortString(String text,int maxSize)
    {
        return (text!=null&&!text.isEmpty()&&text.length()>maxSize)?text.substring(0, maxSize)+"...":text;
    }
    
    /**
     * Remplace les retours à la ligne d'un texte java par des retours à la
     * ligne en HTML
     * @param text {@link String} - Le texte à convertir
     * @return {@link String} Le texte HTML
     */
    public static String getBreakLinesString(String text)
    {
        return text.replaceAll("\n", "<br/>");
    }
    
    /**
     * Appelle un méthode d'une classe sur un objet passé en paramètre
     * avec les arguments donnés
     * @param m {@link Method} - La méthode à invoquer
     * @param desc {@link String} - La description de la méthode
     * @param target {@link Object} - L'ojet sur lequel invoquer la méthode
     * @param args {@link Object}[] - La liste des arguments
     * @return {@link Object} Ce que retourne la méthode
     */
    public static Object callMethod(Method m, String desc, Object target,
            Object... args)
    {
        try
        {
            String call="Appel de la méthode \""+m.getName()+"\" ("+desc+") sur "
                    + "l'objet de la classe \""+target.getClass().getName()+"\" "
                    + "avec les paramètres: "+Arrays.toString(args);
            ApplicationLogger.writeInfo(call);
            return m.invoke(target, args);
        }
        catch (IllegalAccessException ex)
        {
            ApplicationLogger.writeError("La méthode \""+m.getName()+"\" est"+
                    " interdite d'accès pour la classe \""+target.getClass().getName()
                    +"\"", ex);
        }
        catch (IllegalArgumentException ex)
        {
           ApplicationLogger.writeError("Les arguments pour la méthode \""+
                    m.getName()+"\" sont incorrects pour la classe \""+
                    target.getClass().getName()+"\"", ex);
        }
        catch (InvocationTargetException ex)
        {
            ApplicationLogger.writeError("La méthode \""+m.getName()+"\" n'est"+
                    " pas applicable pour la classe \""+target.getClass().getName()+
                    "\"", ex);
        }
        return null;
    }
    
    /**
     * Permet de récupérer les information complètes pour une classe qui possède
     * une méthode "getFullString"
     * @param entity {@link Object} - L'objet sur lequel appeler la méthode "getFullString"
     * @return {@link String} - La description complète de l'objet
     */
    public static String getFullString(Object entity)
    {
        try
        {
            Method getFullString=entity.getClass().getMethod("getFullString");
            String fullString=(String) getFullString.invoke(entity);
            String spacer="\t";
            for(int i=0;i<entity.getClass().getName().length()*2+5;i++)
            {
                spacer+=" ";
            }
            fullString=fullString.replaceAll(", ", 
                                    ",\n"+spacer)
                    .replaceAll("}", "\n"+spacer+"}");
            return fullString;
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.displayError("Impossible de trouver la méthode \""+
                    "getFullString\" pour la classe \""+entity.getClass().getName()+
                    "\"", ex);
        }
        catch (SecurityException ex)
        {
            ApplicationLogger.displayError("Accès refusé à la méthode \""+
                    "getFullString\" pour la classe \""+entity.getClass().getName()+
                    "\"", ex);
        }
        catch (IllegalAccessException ex)
        {
            ApplicationLogger.displayError("Impossible d'accéder à la méthode \""+
                    "getFullString\" pour la classe \""+entity.getClass().getName()+
                    "\"", ex);
        }
        catch (IllegalArgumentException ex)
        {
            ApplicationLogger.displayError("Arguments invalides pour la méthode \""+
                    "getFullString\" pour la classe \""+entity.getClass().getName()+
                    "\"", ex);
        }
        catch (InvocationTargetException ex)
        {
            ApplicationLogger.displayError("Impossible d'appeler à la méthode \""+
                    "getFullString\" pour la classe \""+entity.getClass().getName()+
                    "\"", ex);
        }
        return null;
    }
    
    /**
     * Renvoi la durée sous forme "HH:mm" pour une durée sous la forme "HH h mm mins"
     * @param value {@link String} - Durée sous la forme "HH h mm mins"
     * @return {@link String} - Durée sous la forme "HH:mm"
     */
    public static String getDurationString(Object value)
    {
        String duration=((String)value).substring(0, 2)+":"+((String)value).substring(5, 7);
        return duration;
    }
    
    /**
     * Renvoi la durée sous forme "HH h mm mins" pour une durée sous la forme "HH:mm"
     * @param value {@link String} - Durée sous la forme "HH:mm"
     * @return {@link String} - Durée sous la forme "HH h mm mins"
     */
    public static Object getDurationObject(String value)
    {
        String duration=value;
        return duration.substring(0, 2)+" h "+duration.substring(3, 5)+" mins";
    }
}
