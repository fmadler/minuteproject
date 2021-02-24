package net.sf.minuteProject.configuration.bean.enumeration;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.utils.LicenceUtils;

public enum Extension implements FileComment {

	java {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/**";
		}
		public String textCommentMiddle() {
			return "*";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	},
	php {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/**";
		}
		public String textCommentMiddle() {
			return "*";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return false;
		}
	},
	groovy {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/**";
		}
		public String textCommentMiddle() {
			return "*";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	},
	xml {
		public String lineCommentBeginning() {
			return "<!--";
		}
		public String lineCommentEnding() {
			return "-->";
		}
		public String textCommentBeginning() {
			return "<!--";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "-->";
		}
		public boolean licenceAtBeginning() {
			return false;
		}
	},
	xhtml {
		public String lineCommentBeginning() {
			return "<!--";
		}
		public String lineCommentEnding() {
			return "-->";
		}
		public String textCommentBeginning() {
			return "<!--";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "-->";
		}
		public boolean licenceAtBeginning() {
			return false;
		}
	},
	jsp {
		public String lineCommentBeginning() {
			return "<%--";
		}
		public String lineCommentEnding() {
			return "--%>";
		}
		public String textCommentBeginning() {
			return "<%--";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "--%>";
		}
		public boolean licenceAtBeginning() {
			return false;
		}
	},
	xsd {
		public String lineCommentBeginning() {
			return "<!--";
		}
		public String lineCommentEnding() {
			return "-->";
		}
		public String textCommentBeginning() {
			return "<!--";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "-->";
		}
		public boolean licenceAtBeginning() {
			return false;
		}
	},
	html {
		public String lineCommentBeginning() {
			return "<!--";
		}
		public String lineCommentEnding() {
			return "-->";
		}
		public String textCommentBeginning() {
			return "<!--";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "-->";
		}
		public boolean licenceAtBeginning() {
			return false;
		}
	},
	properties {
		public String lineCommentBeginning() {
			return "#";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "#";
		}
		public String textCommentMiddle() {
			return "#";
		}
		public String textCommentEnding() {
			return "#";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	},
	sql {
		public String lineCommentBeginning() {
			return "--";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/*";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	}
	,css {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/**";
		}
		public String textCommentMiddle() {
			return "*";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	}
	,scss {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/**";
		}
		public String textCommentMiddle() {
			return "*";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	}
	,js {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/*";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	}
	,ts {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/*";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "*/";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	}
	,hbs {
		public String lineCommentBeginning() {
			return "{{!--";
		}
		public String lineCommentEnding() {
			return "--}}";
		}
		public String textCommentBeginning() {
			return "{{!--";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "--}}";
		}
		public boolean licenceAtBeginning() {
			return true;
		}
	}
	;

	private static final String CRLF = "\n";
	private String licence;
	
	public String getLicence() {
		if (licence==null) {
			licence = format(LicenceUtils.getLicence());
		}
		return licence;
	}
	
	public String format (String text) {
		if (text==null) return null;
		String[] lines = StringUtils.split(text, CRLF);
		if (lines.length==0) return null;
		if (lines.length==1) return formatLine(lines[0]);
		return formatText(lines);
	}

	private String formatText(String[] lines) {
		StringBuffer sb = new StringBuffer();
		sb.append(textCommentBeginning()+CRLF);
		for (int i = 0; i < lines.length; i++) {
			sb.append("\t"+textCommentMiddle()+lines[i]+CRLF);
		}
		sb.append(textCommentEnding()+CRLF);
		return sb.toString();
	}

	private String formatLine(String string) {
		return lineCommentBeginning()+string+lineCommentEnding();
	}
	
    public static Extension fromValue(String v) {
    	String checkValue = getCheckValue(v);
        for (Extension c : Extension.values()) {
            if (c.toString().equals(checkValue)) {
                return c;
            }
        }
        return null;
    }
    
    private static String getCheckValue(String v) {
    	if ("jspx".equals(v) ||
    		"jsff".equals(v))
    		return "xml";
		return v;
	}

	//UGLY but for groovy compile...
 	public String lineCommentBeginning() {
 		return "";
 	}
	public String lineCommentEnding() {
		return "";
	}
	public boolean licenceAtBeginning() {
		return false;
	}
}
