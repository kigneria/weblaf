/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.managers.icon.set;

import com.alee.managers.icon.data.IconData;
import com.alee.utils.NetUtils;
import com.alee.utils.TextUtils;
import com.alee.utils.XmlUtils;

import java.io.File;

/**
 * Simple icon set that could be created based on a custom XML data file.
 *
 * @author Mikle Garin
 */

public class XmlIconSet extends AbstractIconSet
{
    /**
     * Constructs new xml-based icon set.
     *
     * @param location icon set data XML file location
     */
    public XmlIconSet ( final String location )
    {
        this ( new File ( location ) );
    }

    /**
     * Constructs new xml-based icon set.
     *
     * @param location icon set data XML file
     */
    public XmlIconSet ( final File location )
    {
        this ( ( IconSetData ) XmlUtils.fromXML ( location ) );
    }

    /**
     * Constructs new xml-based icon set.
     *
     * @param nearClass class to find icon set data XML near
     * @param location  icon set data XML location relative to the specified class
     */
    public XmlIconSet ( final Class nearClass, final String location )
    {
        this ( ( IconSetData ) XmlUtils.fromXML ( nearClass.getResource ( location ) ) );
    }

    /**
     * Constructs new xml-based icon set.
     *
     * @param iconsData icon set data
     */
    public XmlIconSet ( final IconSetData iconsData )
    {
        super ( iconsData.getId () );
        for ( final IconData iconData : iconsData.getIcons () )
        {
            // Adding relative class
            if ( iconData.getNearClass () == null && iconsData.getNearClass () != null )
            {
                iconData.setNearClass ( iconsData.getNearClass () );
            }

            // Combining base path with icon path
            if ( !TextUtils.isEmpty ( iconsData.getBase () ) )
            {
                if ( iconData.getNearClass () != null )
                {
                    iconData.setPath ( NetUtils.joinUrlPaths ( iconsData.getBase (), iconData.getPath () ) );
                }
                else
                {
                    iconData.setPath ( new File ( iconsData.getBase (), iconData.getPath () ).getAbsolutePath () );
                }
            }

            // Adding icon
            addIcon ( iconData );
        }
    }
}