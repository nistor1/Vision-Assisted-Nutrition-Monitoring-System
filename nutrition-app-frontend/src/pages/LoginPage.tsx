import { useState, useEffect } from 'react';
import { Button, Layout, Typography, Form, Input, Divider, Card, Alert } from 'antd';
import { GoogleOutlined, AppleOutlined } from '@ant-design/icons';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import type { AuthRequest, AuthData } from '../types/entities';
import { getRedirectedPath } from '../utils/url';
import { apiService } from '../services/api';

const { Content } = Layout;
const { Title, Text } = Typography;

export default function LoginPage() {
    const { login, isAuthenticated } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleFinish = async (values: AuthRequest) => {
        setLoading(true);
        setError(null);

        try {
            const authData: AuthData = await apiService.login(values);
            login(authData);
            navigate(getRedirectedPath(location.search));
        } catch (err: any) {
            setError(err?.message || 'Login failed');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (isAuthenticated) {
            navigate(getRedirectedPath(location.search));
        }
    }, [isAuthenticated, location.search, navigate]);

    return (
        <Layout>
            <Content style={{ display: 'flex', justifyContent: 'center', paddingTop: 80 }}>
                <Card style={{ width: '100%', maxWidth: 400 }}>
                    <Title level={3} style={{ textAlign: 'center' }}>Welcome Back</Title>
                    <Text type="secondary" style={{ display: 'block', textAlign: 'center', marginBottom: 24 }}>
                        Sign in to your Nutrition account
                    </Text>

                    {error && <Alert message={error} type="error" showIcon style={{ marginBottom: 16 }} />}

                    <Form layout="vertical" onFinish={handleFinish}>
                        <Form.Item label="Username" name="username" rules={[{ required: true }]}>
                            <Input />
                        </Form.Item>

                        <Form.Item label="Password" name="password" rules={[{ required: true }]}>
                            <Input.Password />
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit" block loading={loading}>
                                Log In
                            </Button>
                        </Form.Item>
                    </Form>

                    <Divider>Or continue with</Divider>

                    <div style={{ display: 'flex', gap: 8 }}>
                        <Button icon={<GoogleOutlined />} block onClick={() => alert('Google login coming soon')} />
                        <Button icon={<AppleOutlined />} block onClick={() => alert('Apple login coming soon')} />
                    </div>
                </Card>
            </Content>
        </Layout>
    );
}
