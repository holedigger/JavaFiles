package sample;

public class Games
{

        private String m_hometeam;
        private String m_awayteam;
        private String m_time;

        public Games()
        {
            this.m_hometeam="";
            this.m_awayteam="";
            this.m_time = "";
        }

        public Games(String hometeam, String awayteam, String time)
        {
            this.m_hometeam = hometeam;
            this.m_awayteam = awayteam;
            this.m_time = time;
        }

        public String getM_hometeam()
        {
            return m_hometeam;
        }

        public void setM_hometeam(String m_hometeam)
        {
            this.m_hometeam = m_hometeam;
        }

        public String getM_awayteam()
        {
            return m_awayteam;
        }

        public void setM_awayteam(String m_awayteam)
        {
            this.m_awayteam = m_awayteam;
        }

        public String getM_time()
        {
            return m_time;
        }

        public void setM_time(String m_time)
        {
            this.m_time = m_time;
        }

}
