<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" encoding="utf-8" indent="yes" />
	
	<xsl:variable name="application-name" select="//model/@name" />
	<xsl:variable name="application-version" select="//model/@version" />
	<xsl:variable name="application-package-root" select="//model/@package-root" />
	
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="model">
		<application 
			name="{$application-name}"
			version="{$application-version}"
			package-root="{$application-package-root}"
			>
			<models>
				<xsl:copy>
					<xsl:apply-templates select= "@name | node()" /><!-- just keep name -->
				</xsl:copy>
			</models>
		</application>
	</xsl:template>

</xsl:stylesheet>