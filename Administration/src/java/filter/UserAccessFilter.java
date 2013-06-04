/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import filter.struct.AccessFilter;
import entity.TUser;
import java.util.Arrays;

/**
 *
 * @author Brian GOHIER
 */
public class UserAccessFilter extends AccessFilter
{
    public UserAccessFilter()
    {
        super(Arrays.asList(TUser.ADMIN_RIGHTS,TUser.USER_RIGHTS));
    }   
}
