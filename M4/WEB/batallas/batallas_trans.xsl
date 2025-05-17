<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" indent="yes" />

  <xsl:template match="/battle">
    <html>
      <head>
        <title>Informe de Batalla <xsl:value-of select="@number"/></title>
      </head>
      <body>
        <h1>Batalla <xsl:value-of select="@number"/></h1>

        <h2>Estadísticas de Unidades</h2>
        <table border="1">
          <tr>
            <th>Unidad</th>
            <th>Inicial Planeta</th>
            <th>Bajas Planeta</th>
            <th>Inicial Enemigo</th>
            <th>Bajas Enemigo</th>
          </tr>
          <xsl:for-each select="statistics/unit">
            <tr>
              <td><xsl:value-of select="@name"/></td>
              <td><xsl:value-of select="planet_initial"/></td>
              <td><xsl:value-of select="planet_drops"/></td>
              <td><xsl:value-of select="enemy_initial"/></td>
              <td><xsl:value-of select="enemy_drops"/></td>
            </tr>
          </xsl:for-each>
        </table>

        <h2>Pérdidas de Recursos</h2>
        <h3>Planeta</h3>
        <ul>
          <li>Metal: <xsl:value-of select="resources_losses/planet/metal"/></li>
          <li>Deuterio: <xsl:value-of select="resources_losses/planet/deuterium"/></li>
          <li>Total ponderado: <xsl:value-of select="resources_losses/planet/weighted"/></li>
        </ul>
        <h3>Enemigo</h3>
        <ul>
          <li>Metal: <xsl:value-of select="resources_losses/enemy/metal"/></li>
          <li>Deuterio: <xsl:value-of select="resources_losses/enemy/deuterium"/></li>
          <li>Total ponderado: <xsl:value-of select="resources_losses/enemy/weighted"/></li>
        </ul>

        <h2>Basura Generada</h2>
        <ul>
          <li>Metal: <xsl:value-of select="waste_generated/metal"/></li>
          <li>Deuterio: <xsl:value-of select="waste_generated/deuterium"/></li>
        </ul>

        <h2>Chatarra Recuperada</h2>
        <ul>
          <li>Planeta: <xsl:value-of select="rubble_collected/planet"/></li>
          <li>Enemigo: <xsl:value-of select="rubble_collected/enemy"/></li>
        </ul>

        <h2>Ganador</h2>
        <p><strong><xsl:value-of select="winner"/></strong></p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
