<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema">

    <xsl:output method="text" indent="no"/>

    <xsl:template match="/">
        <xsl:text># Schema Documentation&#xA;&#xA;</xsl:text>
        <xsl:apply-templates select="//xs:element | //xs:complexType | //xs:simpleType | //xs:attribute"/>
    </xsl:template>

    <xsl:template match="xs:element | xs:complexType | xs:simpleType | xs:attribute">
        <xsl:variable name="name">
            <xsl:choose>
                <xsl:when test="local-name() = 'element' or local-name() = 'attribute'">
                    <xsl:value-of select="@name"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="@name"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:text>## </xsl:text>
        <xsl:value-of select="local-name()"/>
        <xsl:text>: </xsl:text>
        <xsl:value-of select="$name"/>
        <xsl:text>&#xA;&#xA;</xsl:text>
        <xsl:if test="xs:annotation/xs:documentation">
            <xsl:text>### Documentation&#xA;</xsl:text>
            <xsl:value-of select="xs:annotation/xs:documentation"/>
            <xsl:text>&#xA;&#xA;</xsl:text>
        </xsl:if>
        <xsl:if test="xs:complexType | xs:simpleType">
            <xsl:apply-templates select="xs:complexType | xs:simpleType"/>
        </xsl:if>
        <xsl:if test="xs:sequence | xs:choice | xs:all">
            <xsl:apply-templates select="xs:sequence | xs:choice | xs:all"/>
        </xsl:if>
    </xsl:template>

    <xsl:template match="xs:sequence | xs:choice | xs:all">
        <xsl:text>### Content Model: </xsl:text>
        <xsl:value-of select="local-name()"/>
        <xsl:text>&#xA;&#xA;</xsl:text>
        <xsl:apply-templates select="xs:element | xs:attribute | xs:complexType | xs:simpleType"/>
    </xsl:template>

</xsl:stylesheet>
