<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                exclude-result-prefixes="xs">

    <xsl:output method="text" encoding="UTF-8"/>

    <!-- Root template -->
    <xsl:template match="/">
        # XSD Schema Documentation
        <xsl:apply-templates select="//xs:element"/>
        <xsl:apply-templates select="//xs:complexType"/>
        <xsl:apply-templates select="//xs:simpleType"/>
    </xsl:template>

    <!-- Element documentation -->
    <xsl:template match="xs:element">
        ## Element: <xsl:value-of select="@name"/>
        <xsl:if test="xs:annotation/xs:documentation">
            **Description:** <xsl:value-of select="xs:annotation/xs:documentation"/>
        </xsl:if>
        <xsl:if test="@type">
            **Type:** <xsl:value-of select="@type"/>
        </xsl:if>
        <xsl:text>&#10;&#10;</xsl:text>
    </xsl:template>

    <!-- ComplexType documentation -->
    <xsl:template match="xs:complexType">
        ## ComplexType: <xsl:value-of select="@name"/>
        <xsl:if test="xs:annotation/xs:documentation">
            **Description:** <xsl:value-of select="xs:annotation/xs:documentation"/>
        </xsl:if>
        <xsl:text>&#10;&#10;</xsl:text>
    </xsl:template>

    <!-- SimpleType documentation -->
    <xsl:template match="xs:simpleType">
        ## SimpleType: <xsl:value-of select="@name"/>
        <xsl:if test="xs:restriction/xs:enumeration">
            **Enumerations:**
            <xsl:for-each select="xs:restriction/xs:enumeration">
                - <xsl:value-of select="@value"/>
            </xsl:for-each>
        </xsl:if>
        <xsl:text>&#10;&#10;</xsl:text>
    </xsl:template>

</xsl:stylesheet>
