package Resource.bdiv3;

import java.util.ArrayList;
import java.util.List;

import jadex.bridge.IExternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
@Service
public class EveServiceBDI extends BobServiceBDI implements IExtendedChService
{
	protected static final List<UserProfileD3> profiles;

	protected UserProfileD3 profile;
	
	static
	{
		profiles = new ArrayList<UserProfileD3>();
		profiles.add(new UserProfileD3("John Doh", 33, false, "I like football, dart and beer."));
		profiles.add(new UserProfileD3("Anna Belle", 21, true, "I like classic music."));
		profiles.add(new UserProfileD3("Prof. Smith", 58, false, "I like Phdcomics."));
		profiles.add(new UserProfileD3("Jim Carry", 44, false, "I like me."));
		profiles.add(new UserProfileD3("Maria Calati", 44, false, "I like flowers and showers."));
	}
	
	/**
	 *  Get the user profile.
	 *  @return The user profile.
	 */
	/*public IFuture<UserProfileD3> getUserProfile()
	{
		if(profile==null)
			this.profile = (UserProfileD3)profiles.get((int)(Math.random()*profiles.size()));
		return new Future<UserProfileD3>(profile);
	}*/
	
	/**
	 *  Create the gui.
	 */
	protected BobGuiBDI createGui(IExternalAccess agent)
	{
		return new EveGuiBDI(agent);
	} {

}

	@Override
	public IFuture<UserProfile> getUserProfile() {
		// TODO Auto-generated method stub
		return null;
	}}
