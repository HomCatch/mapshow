package com.iswater.common.utils;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNUtil {

	private final static String SVN_URL = "http://code.taobao.org/svn/mapshow/trunk/mapShow/";
	private final static String SVN_USERNAME = "/MYZT42iuOYnERi05QPcRw==";
	private final static String SVN_PWD = "DcfyC5dsreg=";
	
	static {
		DESUtil.setKey("sz7road.deci.u21msax8asdj");
	}

	public static long getLastSVNVersion() {
		long latestRevision = -1;
		setupLibrary();
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(SVN_URL));
		} catch (SVNException svne) {
			System.err.println("error while creating an SVNRepository for location '" + SVN_URL + "': " + svne.getMessage());
			System.exit(1);
		}
		String username = DESUtil.getDesString(SVN_USERNAME);
		String pwd = DESUtil.getDesString(SVN_PWD);
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, pwd);
		repository.setAuthenticationManager(authManager);
		try {
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				System.err.println("There is no entry at '" + SVN_URL + "'.");
				System.exit(1);
			} else if (nodeKind == SVNNodeKind.FILE) {
				System.err.println("The entry at '" + SVN_URL + "' is a file while a directory was expected.");
				System.exit(1);
			}
			try {
				latestRevision = repository.getLatestRevision();
			} catch (SVNException svne) {
				System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
				System.exit(1);
			}
		} catch (SVNException svne) {
			System.err.println("error while listing entries: " + svne.getMessage());
			System.exit(1);
		}
		System.out.println("SVN LatestRevision = " + latestRevision);
		return latestRevision;
	}

	private static void setupLibrary() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	public static void main(String[] args) {
		getLastSVNVersion();
	}
}
