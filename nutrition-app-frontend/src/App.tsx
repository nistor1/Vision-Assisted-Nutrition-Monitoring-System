import { MainLayout } from './components/common/Layout';
import AppRouter from './routes/AppRouter';
import { ConfigProvider } from 'antd';
import {AuthProvider} from "./context/AuthContext.tsx";

function App() {
  return (
    <ConfigProvider>
      <AuthProvider>
        <MainLayout>
          <AppRouter />
        </MainLayout>
      </AuthProvider>
    </ConfigProvider>
  );
}

export default App;