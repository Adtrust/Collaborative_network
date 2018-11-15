package Resource.bdiv3;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import jadex.bridge.IComponentStep;
import jadex.bridge.IExternalAccess;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateFuture;
import jadex.commons.future.IIntermediateResultListener;

public class BobGuiBDI extends JFrame {
	
	/** The textfield with received messages. */
	protected JTextArea received;
	
	/** The agent owning the gui. */
	protected IExternalAccess agent;
	protected JFrame f;
	
	//-------- constructors --------
	
	/**
	 *  Create the user interface
	 */
	public BobGuiBDI(IExternalAccess agent)
	{
		super(agent.getComponentIdentifier().getName());
		this.agent	= agent;
		this.setLayout(new BorderLayout());
		
		received = new JTextArea(10, 20);
		final JTextField message = new JTextField();
		JButton send = new JButton("send");
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(message, BorderLayout.CENTER);
		panel.add(send, BorderLayout.EAST);
		
		getContentPane().add(received, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		send.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				final String text = message.getText(); 
				BobGuiBDI.this.agent.scheduleStep(new IComponentStep<Void>()
				{
					public IFuture<Void> execute(IInternalAccess ia)
					{
						IIntermediateFuture<IlogSevice>	fut	= ia.getComponentFeature(IRequiredServicesFeature.class).getRequiredServices("chatservices");
						fut.addResultListener(new IIntermediateResultListener<IlogSevice>()
						{
							public void resultAvailable(Collection<IlogSevice> result)
							{
								for(Iterator<IlogSevice> it=result.iterator(); it.hasNext(); )
								{
									IlogSevice cs = it.next();
									try
									{
										cs.message(BobGuiBDI.this.agent.getComponentIdentifier().getName(), text,text);
									}
									catch(Exception e)
									{
										System.out.println("Could not send message to: "+cs);
									}
								}
							}
							
							public void intermediateResultAvailable(IlogSevice cs)
							{
								System.out.println("found: "+cs);
								cs.message(BobGuiBDI.this.agent.getComponentIdentifier().getName(), text,text);
							}
							
							public void finished()
							{
							}
							
							public void exceptionOccurred(Exception exception)
							{
							}
							
						});
						return IFuture.DONE;
					}
				});
			}
		});
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosed(WindowEvent e)
			{
				BobGuiBDI.this.agent.killComponent();
				BobGuiBDI.this.agent	= null;
			}
		});

		pack();
		setVisible(true);
	}
	
	/**
	 *  Method to add a new text message.
	 *  @param text The text.
	 */
	public void addMessage(final String text)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				received.append(text+"\n");
			}
		});
	}

}
