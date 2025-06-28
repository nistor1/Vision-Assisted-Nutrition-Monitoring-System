import React from 'react';
import { Layout, Typography, Button, Space } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext.tsx';

const { Content, Footer } = Layout;
const { Title, Paragraph } = Typography;

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const { isAuthenticated, user } = useAuth();

  return (
    <Layout>
      <Content>
        <div style={{ padding: 24, textAlign: 'center' }}>
          <Title>Welcome to the Nutrition App</Title>
          <Paragraph>
            Track your meals, monitor your daily nutrients, and get smart food recommendations for a healthier life.
          </Paragraph>
          <Space direction="vertical">
            {isAuthenticated && (
              <Button type="primary" onClick={() => navigate('/meals/new')}>
              Save Meal
            </Button>
            )}
            {isAuthenticated && (
              <Button onClick={() => navigate('/meals/statistics')}>
              View Summary
              </Button>
            )}
            <Button onClick={() => navigate('/food-items')}>
              Browse Available Food
            </Button>
          </Space>
        </div>
      </Content>

      <Footer>
        <div style={{textAlign: 'center'}}>
          <Space direction="vertical">
            {isAuthenticated && (
              <>
                <Button block onClick={() => navigate('/meals')}>Manage Meals</Button>
              </>
            )}
            {user?.role === 'ADMIN' && (
              <Button block type="dashed" onClick={() => navigate('/admin/users')}>
                Manage Users
              </Button>
            )}
          </Space>
        </div>
      </Footer>
    </Layout>
);
};

export default HomePage;
