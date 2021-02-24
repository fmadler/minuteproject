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
				<model name="porphyry" version="1.0" package-root="net.sf.mp.demo">
					<description>
						<CDATA>
							This model illustrates:
							Reverse-engineering on tables, views
							SDD
							Add ad-hoc forms and actions
						</CDATA>
					</description>
					<data-model>
						<driver name="mysql" version="5.1.16" groupId="mysql"
							artifactId="mysql-connector-java"></driver>
						<dataSource>
							<driverClassName>org.gjt.mm.mysql.Driver</driverClassName>
							<url>jdbc:mysql://localhost:3306/porphyry</url>
							<username>root</username>
							<password>mysql</password>
						</dataSource>
						<!-- for Oracle and DB2 please set the schema <schema> </schema> -->
						<primaryKeyPolicy oneGlobal="true">
							<primaryKeyPolicyPattern name="autoincrementPattern"></primaryKeyPolicyPattern>
						</primaryKeyPolicy>
					</data-model>
					<business-model>
						<business-package default="project">
							<condition type="package" startsWith="STAT" result="statistics"></condition>
							<condition type="package" startsWith="SEC" result="security"></condition>
							<condition type="package" startsWith="ADMIN" result="admin"></condition>
							<condition type="package" startsWith="RELEASE" result="release"></condition>
							<condition type="package" startsWith="BUS" result="business"></condition>
							<condition type="package" startsWith="VIEW_STAT" result="stats"></condition>
						</business-package>
						<enrichment>
							<conventions>
								<column-naming-convention type="apply-strip-column-name-suffix"
									pattern-to-strip="_ID" />
								<reference-naming-convention
									type="apply-referenced-alias-when-no-ambiguity" is-to-plurialize="true" />
								<ordering-convention field-pattern="DISPLAY_ORDER"
									field-pattern-type="endsWith" ordering="asc" />
		
								<!-- stereotype email, URL -->
								<stereotype-convention field-pattern="email"
									field-pattern-type="contains" stereotype="email" />
								<stereotype-convention field-pattern="url"
									field-pattern-type="contains" stereotype="url" />
								<stereotype-convention field-pattern="password"
									field-pattern-type="contains" stereotype="password" />
								<stereotype-convention field-pattern="notes"
									field-pattern-type="equals" stereotype="text-html" />
		
								<!-- primary key conv -->
								<column-naming-convention
									type="apply-fix-primary-key-column-name-when-no-ambiguity-and-not-natural"
									default-value="ID" />
		
								<!-- strip prefix -->
								<entity-naming-convention type="apply-strip-table-name-prefix"
									pattern-to-strip="ADMIN_,SEC_,BUS_" />
								<!-- semantic reference -->
								<semantic-entity-convention
									field-pattern="NAME,NUMBER" field-pattern-type="contains"
									max-number-of-fields="2" />
		
								<!-- trigger -->
								<property-convention scope="field" pattern="CREATION_DATE"
									tag="trigger-insert-with-current-date" type="start-with" />
								
								<view-primary-key-convention 
								            type="apply-default-primary-key-otherwise-first-one" 
								            default-primary-key-names="IDENTIFIER,ID" /> 
							</conventions>
		
							<entity name="ADMIN_ENVIRONMENT" content-type="reference-data">
								<semantic-reference>
									<sql-path path="NAME" />
								</semantic-reference>
							</entity>
							<entity name="SEC_USER">
								<semantic-reference>
									<sql-path path="IDENTIFIER" />
								</semantic-reference>
							</entity>
							<entity name="BUS_RELEASE">
								<!-- todo trigger name -->
								<!-- todo trigger creation date -->
								<field name="STATUS">
									<property tag="checkconstraint" alias="release_status">
		
										<property name="ACTIVE" value="ACTIVE" />
										<property name="DONE" value="DONE" />
										<property name="NOT_OK" value="REJECTED" />
									</property>
								</field>
							</entity>
							<entity name="SEC_PROJECT_RIGHT">
								<field name="role">
									<property tag="checkconstraint">
										<property name="RELEASER" />
										<property name="RELEASE_VALIDATOR" />
									</property>
								</field>
							</entity>
							<entity name="bus_user_role_request">
								<field name="STATUS">
									<property tag="checkconstraint">
										<property name="TO_TREAT" />
										<property name="TREATED" />
										<property name="REJECTED" />
									</property>
								</field>
							</entity>
							<!-- batch with SDD -->
		
							<!-- transient objects -->
							<entity name="create release" is-transfer-entity="true" is-searchable="false"
								main-entity="bus_release">
								<field name="id" hidden="true"></field>
								<field name="name"></field>
								<field name="notes"></field>
								<field name="application_id"></field>
								<field name="environment_id"></field>
								<field name="releaser_id" ></field>
								<field name="status" value="ACTIVE" hidden="true"></field>
								<field name="creation_date" hidden="true"></field>
								<!-- <field name="validator_id"></field>  -->
								<field name="time_to_start"></field>
								<field name="time_to_end"></field>
								<field name="validation_reason" hidden="true"></field>
								<!-- other fields -->
								<field name="notify" type="string" mandatory="true">
									<stereotype stereotype="EMAIL" />
								</field>
								<actions>
									<action name="release" default-implementation="insert" >
										<field name="status" value="ACTIVE"></field>
									</action>
								</actions>
							</entity>
							<!-- transient objects -->
							<entity name="validate release" is-transfer-entity="true"
								main-entity="bus_release" is-editable="false" is-searchable="true">
								<field name="id" hidden="true"></field>
								<field name="name"></field>
								<field name="notes"></field>
								<field name="application_id"></field>
								<field name="environment_id"></field>
								<field name="releaser_id"></field>
								<field name="status"></field>
								<field name="creation_date"></field>
								<field name="validator_id" is-editable="true"/><!-- default-value="profile.user.id"></field> -->
								<field name="time_to_start"></field>
								<field name="time_to_end"></field>
								<field name="validation_reason" is-editable="true"></field>
								<!-- other fields -->
								<field name="inform" type="string" mandatory="true"
									is-editable="true">
									<stereotype stereotype="EMAIL" />
								</field>
								<actions>
									<action name="validate" default-implementation="update">
										<action-condition name="statusActive">
											<field name="status" value="ACTIVE"/>
										</action-condition>
										<field name="status" value="DONE"></field>
									</action>
									<action name="reject" default-implementation="update">
										<action-condition name="statusActive">
											<field name="status" value="ACTIVE"/>
										</action-condition>
										<field name="status" value="REJECT"></field>
									</action>						
								</actions>
							</entity>
						</enrichment>
					</business-model>
					<!-- S D D -->
					<statement-model>
						<queries>
					         <query name="ask_for_role" id="ask_for_role" >
		                         <query-body>
		                         <value>
		<![CDATA[call ask_for_role (?,?,?)]]>
		                            </value>
		                         </query-body>
		                         <query-params>
		                             <query-param name="username" is-mandatory="true" type="string" sample="'a'" is-id="true"></query-param>
		                             <query-param name="email" is-mandatory="true" type="string" sample="'b'">
		                             	<stereotype stereotype="EMAIL" />
		                             </query-param>
		                             <query-param name="role" is-mandatory="true" type="string" sample="'c'">
		                                <query-param-link entity-name="sec_role" field-name="role"/>
		                             </query-param>
		                         </query-params>
		                     </query>
		                     				
					         <query name="role_request_per_day" id="role_request_per_day" >
		                         <query-body>
		                         <value>
		<![CDATA[SELECT DAY(request_date) as requestDate, COUNT(*) as request
		FROM bus_user_role_request
		GROUP BY DAY(request_date) order by DAY(request_date)]]>
		                            </value>
		                         </query-body>
		                         <query-params>
		                         </query-params>
		                     </query>
					         <query name="release_info_per_status" id="role_request_per_day" >
		                         <query-body>
		                         <value>
		<![CDATA[SELECT DAY(creation_date) as creationDate, COUNT(*) as request
		FROM bus_release
		where status = ?
		GROUP BY DAY(creation_date) order by DAY(creation_date)]]>
		                            </value>
		                         </query-body>
		                         <query-params>
		                             <query-param name="status" is-mandatory="true" type="string" sample="'a'" is-id="true">
										<property tag="checkconstraint">
											<property name="TO_TREAT" />
											<property name="TREATED" />
										</property>
		                             </query-param>
		                             
		                         </query-params>
		                     </query>
		
					         <query name="select pending user" id="select-pending-user" >
		                         <query-body>
		                         <value>
		<![CDATA[select * from bus_user_role_request where status = 'TO_TREAT' order by request_date asc]]>
		                            </value>
		                         </query-body>
		                     </query>
					         <query name="grant user" id="grant-user" >
		                         <query-body>
		                         <value>
		<![CDATA[call grant_user (?)]]>
		                            </value>
		                         </query-body>
		                         <query-params>
		                             <query-param name="user_role_request_id" is-mandatory="true" type="long" sample="1" is-id="true"></query-param>
		                         </query-params>
		                     </query>                     
					         <query name="revoke user" id="revoke-user" >
		                         <query-body>
		                         <value>
		<![CDATA[call reject_user (?)]]>
		                            </value>
		                         </query-body>
		                         <query-params>
		                             <query-param name="user_role_request_id" is-mandatory="true" type="long" sample="1" is-id="true"></query-param>
		                         </query-params>
		                     </query>                     
		                </queries>
		                <nested-statements>
		                	<nested-statement name="manage access" query-id="select-pending-user">
								<actions>
									<action name="grant user" default-implementation="statement" 
									    query-id="grant-user-role" >
									</action>					
									<action name="reject user" default-implementation="statement" 
									    query-id="reject-user-role" >
									</action>					
								</actions>
		                	</nested-statement>
		                </nested-statements>
		            </statement-model>
				</model>
			</models>
		</application>
	</xsl:template>

</xsl:stylesheet>