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
			name="appli"
			version="{$application-version}"
			package-root="{$application-package-root}"
			>
			<models>
				<xsl:copy>
					<xsl:apply-templates select="@name | node()" />
				</xsl:copy>
				<!-- set devops -->
				<model name="devops">
					<data-model>
						<driver name="mysql" version="5.1.16" groupId="mysql"
							artifactId="mysql-connector-java" />
						<dataSource>
							<driverClassName>org.gjt.mm.mysql.Driver</driverClassName>
							<url>jdbc:mysql://localhost:3306/mp_devops</url>
							<username>root</username>
							<password>mysql</password>
						</dataSource>
						<primaryKeyPolicy oneGlobal="false">
							<primaryKeyPolicyPattern name="autoincrementPattern" />
						</primaryKeyPolicy>
					</data-model>
					<business-model>
						<business-package default="devops">
							<condition type="package" startsWith="MP_I18N" result="i18n" />
							<condition type="package" startsWith="MP_RIGHT" result="right" />
						</business-package>
						<enrichment>
							<conventions>
								<table-default-primary-key-convention
									type="apply-default-primary-key-otherwise-first-one"
									default-primary-key-names="ID" />
								<view-primary-key-convention
									type="apply-default-primary-key-otherwise-first-one"
									default-primary-key-names="IDENTIFIER,ID" />
							</conventions>
						</enrichment>
					</business-model>
				</model>
			</models>
		</application>
	</xsl:template>

</xsl:stylesheet>