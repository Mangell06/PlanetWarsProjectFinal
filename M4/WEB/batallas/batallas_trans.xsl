<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" indent="yes"/>

  <xsl:template match="/batallas_transform">
    <html>
      <head>
        <title>Informe Batalla <xsl:value-of select="@number"/></title>
      </head>
      <body>
        <h1>Batalla <xsl:value-of select="@number"/></h1>
        <statistics>
            <p><strong>Light Hunter</strong>
            <unit name="Light Hunter">
                <xsl:for-each select="unit[@name='Light Hunter']">
                    <xsl:value-of select="@planet_initial"/>
                    <xsl:value-of select="@planet_drops"/>
                    <xsl:value-of select="@enemy_initial"/>
                    <xsl:value-of select="@enemy_drops"/>
                </xsl:for-each>
            </unit>
            </p>
            <p><strong>Heavy Hunter</strong>
            <unit name="Heavy Hunter">
                <xsl:for-each select="unit[@name='Heavy Hunter']">
                    <xsl:value-of select="@planet_initial"/>
                    <xsl:value-of select="@planet_drops"/>
                    <xsl:value-of select="@enemy_initial"/>
                    <xsl:value-of select="@enemy_drops"/>
                </xsl:for-each>
            </unit>
            </p>
            <p><strong>Battle Ship</strong>
            <unit name="Battle Ship">
                <xsl:for-each select="unit[@name='Battle Ship']">
                    <xsl:value-of select="@planet_initial"/>
                    <xsl:value-of select="@planet_drops"/>
                    <xsl:value-of select="@enemy_initial"/>
                    <xsl:value-of select="@enemy_drops"/>
                </xsl:for-each>
            </unit>
            </p>
            <p><strong>Armored Ship</strong>
            <unit name="Armored Ship">
                <xsl:for-each select="unit[@name='Armored Ship']">
                    <xsl:value-of select="@planet_initial"/>
                    <xsl:value-of select="@planet_drops"/>
                    <xsl:value-of select="@enemy_initial"/>
                    <xsl:value-of select="@enemy_drops"/>
                </xsl:for-each>
            </unit>
            </p>
            <p><strong>Missile Launcher</strong>
            <unit name="MIsseile Launcher">
                <xsl:for-each select="unit[@name='Missile Launcher']">
                    <xsl:value-of select="@planet_initial"/>
                    <xsl:value-of select="@planet_drops"/>
                    <xsl:value-of select="@enemy_initial"/>
                    <xsl:value-of select="@enemy_drops"/>
                </xsl:for-each>
            </unit>
            </p>
            <p><strong>Ion Cannon</strong>
            <unit name="Ion Cannon">
                <xsl:for-each select="unit[@name='Ion Cannon']">
                    <xsl:value-of select="@planet_initial"/>
                    <xsl:value-of select="@planet_drops"/>
                    <xsl:value-of select="@enemy_initial"/>
                    <xsl:value-of select="@enemy_drops"/>
                </xsl:for-each>
            </unit>
            </p>
            <p><strong>Plasma Cannon</strong>
            <unit name="Plasma Cannon">
                <xsl:for-each select="unit[@name='Plasma Cannon']">
                    <xsl:value-of select="@planet_initial"/>
                    <xsl:value-of select="@planet_drops"/>
                    <xsl:value-of select="@enemy_initial"/>
                    <xsl:value-of select="@enemy_drops"/>
                </xsl:for-each>
            </unit>
            </p>
        </statistics>
        <resources_losses>
            <p><strong>Planet</strong>
            <planet>
                <xsl:for-each select="resources_losses[@name='planet']">
                    <xsl:value-of select="@metal"/>
                    <xsl:value-of select="@dueterium"/>
                    <xsl:value-of select="@weighted"/>
                </xsl:for-each>
            </planet>
            </p>
            <p><strong>Enemy</strong>
            <enemy>
                <xsl:for-each select="resources_losses[@name='enemy']">
                    <xsl:value-of select="@metal"/>
                    <xsl:value-of select="@dueterium"/>
                    <xsl:value-of select="@weighted"/>
                </xsl:for-each>
            </enemy>
            </p>
        </resources_losses>
        <waste_generated>
            <p><strong>Metal:</strong>
            <xsl:for-each select="waste_generated[@name='metal']">
                <xsl:value-of select="@value"/>
            </p>
            <p><strong>Dueterium:</strong>
            <xsl:for-each select="waste_generated[@name='deuterium']">
                <xsl:value-of select="@value"/>
            </p>
        </waste_generated>
        <rubble_collected>
            <p><strong>Planet:</strong>
            <xsl:for-each select="rubble_collected[@name='planet']">
                <xsl:value-of select="@value"/>
            </p>
            <p><strong>Enemy:</strong>
            <xsl:for-each select="rubble[@name='enemy']">
                <xsl:value-of select="@value"/>
            </p>
      </body>
      <p><strong>Winner:</strong><xsl:value-of select="@winner"/></p>
    </html>
  </xsl:template>
</xsl:stylesheet>